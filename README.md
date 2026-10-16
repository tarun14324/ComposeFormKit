
# Compose Form Kit

## Overview

A config-driven lead capture form built using Kotlin and Jetpack Compose.

The form is driven by field configuration and supports reusable
UI components, validation, conditional fields, and responsive layouts.

Minimum Android SDK: Match the `minSdk` configured in `app/build.gradle.kts`.

## Build and Run

1. Open the project in Android Studio.
2. Sync the Gradle project.
3. Run the application on an Android emulator or device.
4. Test on a compact phone layout and a wide screen or tablet.
5. Verify validation, conditional fields, keyboard behavior,
   and responsive layout.

Alternatively, build using:

```bash
./gradlew assembleDebug
```

## Architecture

- `design_system/tokens`: Colors, spacing, typography, dimensions.
- `design_system/atoms`: Generic text, select, checkbox, and button controls.
- `design_system/components`: Field molecule and reusable UI components.
- `lead/config`: Field metadata, rules, and form configuration.
- `lead/validation`: Pure Kotlin form validation.
- `lead/ui`: Lead form screen and configuration-driven rendering.
- `data/local`: Room entity, DAO, and database.
- `data/repository`: Repository abstraction for lead persistence.

## Adding a Field

1. Add a `FieldConfig` entry.
2. Specify its field type, label, rules, and optional visibility condition.
3. Use an existing `FieldType` when possible.
4. Ensure the renderer maps the field type to the appropriate atom.
5. Add validation behavior if a new rule is required.

## Validation

`LeadFormValidator` accepts field configuration and form values,
and returns a map of field names to error messages.

Validation is independent of Android UI code and handles
conditional visibility and field rules.

## Responsive Layout

The form uses a width breakpoint to choose a compact or expanded layout.
Compact screens use one column; wider screens can use two columns.
Notes and consent occupy the full available width.

## Design System

Atoms provide generic controls. Field labels, hints, error messages,
and lead-specific validation are handled outside the atoms.

## Persistence

Room stores submitted leads locally. The repository exposes
lead observation, insertion, and deletion operations.
