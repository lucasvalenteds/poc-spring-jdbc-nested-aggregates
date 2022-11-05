# POC: Spring Data JDBC Nested Aggregates

It demonstrates how persistence works for an aggregate root with nested aggregates.

The goal is to implement the persistence of data related to incidents. We want to be able to register incidents such as
database disruption and system failures, document interventions made to solve them and add notes to each intervention to
make it clear what we've made and when. Based on that use case we defined three main aggregates: `Incident`
, `Intervention` and `Note`. The `Incident` is the root, the entry point for persistence and the one that has
a `Repository`. It has a list of `Intervention`, each one containing a list of `Note`. We also want to have an aggregate
named `InterventionType` which is another root aggregate referenced by the `Intervention` aggregate.

The source code targets a Postgres database, which is provisioned automatically by Testcontainers during test executing
and with schemas managed by Flyway. No manual action is required to run the code and there is no need for a Web layer or
other user interface.

## How to run

| Description | Command          |
|-------------|------------------|
| Run tests   | `./gradlew test` |

## Preview

Example of incident serialized to JSON:

```json
{
  "id": 1,
  "description": "Customer are unable to login",
  "createdAt": "2022-11-05T01:02:39.612052Z",
  "interventions": [
    {
      "id": 1,
      "name": "PHONE_CALL",
      "createdAt": "2022-11-05T01:02:39.670929Z",
      "notes": [
        {
          "content": "Called the Ops teams and told John two Web Services were down",
          "createdAt": "2022-11-05T01:02:39.670647Z"
        },
        {
          "content": "The Ops team returned the call and told the Web Services were restored",
          "createdAt": "2022-11-05T01:02:39.718177Z"
        }
      ]
    },
    {
      "id": 2,
      "name": "ENQUEUE",
      "createdAt": "2022-11-05T01:02:39.700670Z",
      "notes": [
        {
          "content": "Enqueued the incident while we wait the Ops team response",
          "createdAt": "2022-11-05T01:02:39.700666Z"
        }
      ]
    }
  ]
}
```
