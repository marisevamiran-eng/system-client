# System Client — Minecraft 1.21.11

Fabric client project configured for Minecraft Java Edition 1.21.11 and Java 21.

## Build locally

```bash
./gradlew --no-daemon clean build
```

The distributable JAR is written to `build/libs/`.

## Build with GitHub Actions

Push this repository to GitHub. The workflow in `.github/workflows/build.yml` runs on pushes, pull requests, and manual dispatch. Open **Actions → Build Fabric mod → latest run → Artifacts** to download the JAR.

## Notes

The build configuration and Fabric metadata are targeted at 1.21.11. Minecraft/Fabric API changes can require source-level porting; the CI build will report any remaining Java or mixin incompatibilities.
