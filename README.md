# DSA-Visualizer

DSA-Visualizer is a three-part educational system for exploring data structures:

1. `custom-collections` implements the data structures in Java.
2. `dsa-backend` executes operations against those structures and records a visual trace.
3. `dsa-frontend` lets a user queue operations and play the trace back as an animation.

The root Maven project builds the two Java modules. The React frontend is a separate Vite application and communicates with the backend over HTTP.

## Architecture At A Glance

```text
React workspace
	-> module API client: POST /api/{structure}/execute
	-> Spring Boot controller
	-> traced collection adapter
	-> custom collection implementation
	-> ExecutionTrace: ordered Step objects
	-> React step player and structure-specific visualization
```

The central architectural idea is separation between mutation and explanation. The custom collection performs the data-structure operation; the backend's traced adapter performs the same operation while recording snapshots, highlighted indexes, helper values, and human-readable descriptions for the frontend.

## 1. Custom Collections

### Module role

`custom-collections` is a Java 17 library module. It is packaged as a JAR and is a dependency of `dsa-backend`. Its purpose is to provide implementations of collection behavior rather than using Java's standard collection implementations directly.

### Package structure

```text
custom-collections/src/main/java/com/dsa/collections/
	interfaces/       Collection contracts and sequenced operations
	iterator/         JIterator abstraction
	linear/           Array, linked-list, stack, queue, and set implementations
	associative/      Map contracts and tree-map implementation
	exceptions/       Collection-specific exception types
```

### Abstraction layer

- `JIterable` is the base iteration contract.
- `JIterator` supplies the custom iterator abstraction.
- `JCollection` defines common collection operations such as `size`, `isEmpty`, `contains`, `toArray`, `add`, `remove`, and `clear`.
- `JSequencedCollection` extends the common collection behavior with ordered operations such as first, last, and reverse access.
- `JList` adds indexed access, indexed insertion/removal, copying, and sorting.
- `JQueue` defines FIFO operations: `enqueue`, `dequeue`, and `peek`.
- `JSet` and the associative map contracts support the set/map implementations.

### Implementations

- `JArrayList` uses a backing `Object[]`, tracks logical `size` separately from `capacity`, and grows its capacity when full. It provides indexed access, insertion, removal, iteration, and sequence operations.
- `JLinkedList` uses a doubly linked node chain with `first`, `last`, and `size` state. It supports traversal, indexed/list operations, and efficient end operations.
- `JStack` extends `JArrayList` and adds LIFO behavior through `push`, `pop`, and `peek`, with a `top` index.
- `JLinkedQueue` extends `JLinkedList` and maps queue behavior to list endpoints: enqueue at the rear and dequeue/peek at the front.
- `JHashSet` provides set semantics, including duplicate handling.
- `JTreeMap` provides the associative tree-map implementation.

The inheritance relationships intentionally reuse the list implementations for the stack and queue, keeping the learning model visible: a stack is an array-backed sequence with a top boundary, while a queue is a linked sequence with front/rear operations.

### Tests and build role

The module uses JUnit Jupiter and Maven Surefire. Tests are organized beside the implementation concepts, including focused tests for `JArrayList`, `JLinkedList`, `JStack`, `JQueue`, `JHashSet`, and `JTreeMap`.

## 2. DSA Backend

### Module role

`dsa-backend` is a Spring Boot 4.1.0 web application running on Java 17. It depends on `custom-collections` and exposes an operation-execution API for the visualizer. The application entry point is `DsaBackendApplication`.

### Package structure

```text
dsa-backend/src/main/java/com/dsa/dsa_backend/
	controller/      REST endpoints for each collection type
	dto/             Request, operation, and response payloads
	engine/          Step model, recorder, trace, and step types
	traced/          Instrumented adapters around custom collections
```

### Request and execution pipeline

1. A controller accepts an `ExecutionRequest` containing a non-empty list of `Operation` objects.
2. Each operation contains a name, such as `add`, `remove`, `push`, or `linearSearch`, plus an argument list.
3. The controller creates a fresh `StepRecorder` and a structure-specific traced adapter.
4. Operations are processed in request order through a switch on the operation name.
5. The traced adapter delegates actual state changes to the corresponding custom collection and records a step after meaningful execution events.
6. The recorder builds an immutable `ExecutionTrace`.
7. The controller returns an `ExecutionResponse` containing `steps` and `totalSteps`.

### REST controllers

The controllers follow a consistent `POST /api/{structure}/execute` shape:

| Structure | Endpoint | Controller | Traced adapter |
| --- | --- | --- | --- |
| Array list | `/api/arraylist/execute` | `JArrayListController` | `TracedJArrayList` |
| Linked list | `/api/linkedlist/execute` | `JLinkedListController` | `TracedJLinkedList` |
| Stack | `/api/stack/execute` | `JStackController` | `TracedJStack` |
| Queue | `/api/queue/execute` | `JQueueController` | `TracedJQueue` |
| Hash set | `/api/hashset/execute` | `JHashSetController` | `TracedJHashSet` |
| Tree map | `/api/treemap/execute` | `JTreeMapController` | `TracedJTreeMap` |

This controller-per-structure design keeps endpoint operation dispatch explicit and makes each data structure's supported operation set easy to inspect. The traced classes are the backend's adapter layer: they contain visualization-specific recording logic without modifying the reusable collection library.

### Trace engine

The `engine` package defines the backend/frontend contract for playback:

- `Step` is one immutable conceptual state in an execution. It contains a `StepType`, a collection `snapshot`, highlighted indexes, named integer helper values, and a description.
- `StepType` categorizes events such as `ADD`, `REMOVE`, `SET`, `GET`, `COMPARE`, `SHIFT`, `FOUND`, `NOT_FOUND`, `SWAP`, `SORT`, `FAILED`, `MIDDLE`, `REVERSED`, and `EXISTING`.
- `StepRecorder` accumulates steps for one request and creates a trace when execution finishes.
- `ExecutionTrace` exposes the recorded steps as an unmodifiable list and reports the total count.
- `ExecutionResponse` converts the trace into the JSON response consumed by React.

The snapshot-based contract means the frontend does not need to reproduce the collection algorithm. It only needs to render the current snapshot and interpret the metadata for the current step.

### Dependencies and validation

The backend uses Spring Web MVC for REST APIs, Spring validation for request constraints, Lombok as an optional compile-time dependency, and Spring Boot test starters for tests. The current backend test source contains the application-context test; collection behavior is primarily tested in the `custom-collections` module.

## 3. Frontend Architecture

This section intentionally covers only the ArrayList, LinkedList, Stack, and Queue areas. The frontend HashSet and TreeMap modules are excluded from this review as requested.

### Application shell

```text
dsa-frontend/src/
	main.jsx              React entry point and global stylesheet import
	App.jsx               top-level module selection
	pages/Home.jsx        structure selection screen
	shared/
		api/                shared backend client
		components/         reusable workspace controls
		constants/          shared step-type constants
		hooks/              structure-agnostic trace playback
	modules/
		arraylist/          ArrayList workspace, API, config, animation, output UI
		linkedlist/         LinkedList workspace, API, config, animation, output UI
		stack/              Stack workspace, API, config, animation, output UI
		queue/              Queue workspace, API, config, animation, output UI
```

The application currently uses state-based navigation in `App.jsx`: `activeModule` selects a workspace and `null` displays `Home`. React Router is not used.

### Shared interaction model

Each included workspace follows the same local state machine:

- `queue` stores operations selected by the user.
- `trace` stores the backend response after execution.
- `isLoading` and `error` represent request state.
- `useStepPlayer` tracks the current step, play/pause state, speed, and next/previous/reset controls.
- The current step is passed to the module's `OutputWindow` for rendering.

Shared components keep this workflow consistent:

- `OperationPicker` displays the operations defined by a module's configuration.
- `ArgumentForm` collects operation arguments.
- `OperationQueueList` displays queued operations and supports removal.
- `PlaybackControls` controls trace navigation and playback speed.
- `BackExecuteBar` handles navigation back to Home and submitting the queue.
- `WorkspaceHeader` identifies the active structure.
- `Button` provides common button styling.

### Module boundaries

Each included data-structure module has the same internal shape:

```text
modules/{structure}/
	{Structure}Workspace.jsx   local state and composition root
	api/{structure}Api.js      endpoint-specific API wrapper
	config/operationsConfig.js supported operations and form arguments
	animations/                step-type animation variants
	components/                visualization and output components
```

The module-specific API wrapper supplies the endpoint and delegates the HTTP work to `shared/api/client.js`. The operation configuration drives both the operation picker and the argument form, which prevents the UI controls and submitted operation shape from drifting apart.

### Backend integration

`shared/api/client.js` sends a JSON `POST` body shaped as `{ operations }` and returns the decoded JSON response. In development, Vite proxies relative `/api` requests from port `5173` to Spring Boot on port `8080`; this avoids requiring browser-side CORS configuration. A `VITE_API_BASE_URL` value can be used when the frontend and backend are deployed separately and the backend provides the required CORS setup.

### Playback and visualization flow

```text
Select operation
	-> ArgumentForm creates { name, arguments }
	-> operation enters workspace queue
	-> Execute posts the queue to the matching backend endpoint
	-> response.steps becomes the workspace trace
	-> useStepPlayer selects one step at a time
	-> OutputWindow and animation variants render snapshot + metadata
```

The playback hook is intentionally structure-agnostic. Structure-specific rendering stays inside each module, while timing and navigation are shared. This is the main frontend reuse boundary.

## Running The Project

### Build and test the Java modules

Run from the repository root:

```cmd
mvn clean install
```

To compile the custom collection tests without running the full reactor:

```cmd
cd custom-collections
mvn test-compile
```

The normal Maven test command is preferred for executing the JUnit suite:

```cmd
cd custom-collections\src
java -cp target\classes;target\test-classes com.dsa.collections.linear.
```

### Start the backend

From the repository root:

```cmd
mvn spring-boot:run -pl dsa-backend
```

The frontend development proxy expects the backend at `http://localhost:8080`.

### Start the frontend

```cmd
cd dsa-frontend
npm install
npm run dev
```

Vite serves the application at its configured development port, `5173`, and forwards `/api` calls to the backend.

## Presentation Summary

The project is best presented as a layered teaching system:

- **Implementation layer:** custom Java collections expose the underlying data-structure mechanics.
- **Instrumentation layer:** traced adapters turn algorithm events into serializable visual steps.
- **API layer:** Spring controllers provide one consistent execute endpoint per structure.
- **Experience layer:** React workspaces let users compose operations and inspect execution one step at a time.

This arrangement keeps the data structures reusable, the visualization protocol explicit, and the frontend focused on interaction and explanation rather than algorithm implementation.
