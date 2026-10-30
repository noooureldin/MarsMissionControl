# MARS-OPS

MARS-OPS is a Java simulation of a Mars colony. It models colony agents,
facilities, resources, missions, environmental conditions, and emergency
response operations.

## Features

- Register and manage colony agents and facilities.
- Submit missions after checking agent capabilities, environment, and resources.
- Advance mission time and release agents when missions finish.
- Aggregate and consume facility resources.
- Handle emergencies such as oxygen leaks and rover breakdowns.
- Demonstrate successful and rejected operations from a single `Main` class.

## Project structure

```text
src/
├── Main.java
├── agents/       Agents, crew members, robots, rovers, and specialist roles
├── colony/       Colony coordination, simulation, and environment abstraction
├── common/       Shared enums and value objects
├── emergencies/  Emergency types and emergency coordination
├── environment/  Environmental state and conditions
├── facilities/   Habitats, laboratories, storage, power, and inventories
├── missions/     Mission types and mission coordination
└── resources/    Resource aggregation and consumption utilities
```

The UML class diagram is available at
[`docs/class-diagram.puml`](docs/class-diagram.puml).

## Requirements

- Java Development Kit (JDK) 11 or later
- A Java IDE such as IntelliJ IDEA or VS Code is optional

No external libraries or build tools are required.

## Compile and run

From the project root in PowerShell:

```powershell
Set-Location .\src
New-Item -ItemType Directory -Force .\out | Out-Null
javac -d .\out @(Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
java -cp .\out Main
```

The program prints five demonstration scenarios:

1. Successful exploration mission.
2. Research mission rejected because the engineer lacks the research capability.
3. Research mission rejected because the requested battery power exceeds supply.
4. Oxygen leak emergency handling.
5. Exploration mission rejected during a dust storm.

To clean compiled output:

```powershell
Remove-Item -Recurse -Force .\out
```

## Running in an IDE

Open the `src` directory as the source root, open `Main.java`, and run the
`main` method. The project includes `src.iml` for IntelliJ IDEA module
configuration.

## Generating the UML diagram

The diagram source uses PlantUML. Install PlantUML or use a PlantUML-compatible
IDE extension, then render:

```text
docs/class-diagram.puml
```

The diagram can be exported as PNG or SVG for reports and presentations.

