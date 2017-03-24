# LendingLibrary

A shadeable lib used in many of my projects.

## How to add to your project

### There are two modules for this library

1. Standard
    * Includes all packages and classes.
    * Should be updated each Minecraft version
2. Forge (append artifact with `:forge`)
    * Excludes the `tehnut.lib.mc` package.
    * Only touches Forge classes at the most, so it should be relatively stable across Minecraft versions

It's a fairly simple process. Just add the following bits of code to your `build.gradle`.

* Directly after you apply all of your plugins, add this `configurations {}` block. If you already have one, add to it.

        configurations {
            shade
            compile.extendsFrom shade
        }
    
* In your `repositories {}` block, add `maven { url "http://tehnut.info/maven" }`.

* In your `dependencies {}` block, add `shade "tehnut.lib:LendingLibrary:<MINECRAFT_VERSION>-<LL_VERSION>"`. Replace `<MINECRAFT_VERSION>` with the Minecraft version you want. Replace `<LL_VERSION>` with the LendingLibrary version you want.

* Add this `reobf {}` block anywhere below the `configurations {}` block. This will pack `tehnut.lib` into `your.package.base.shade.lib`. Make sure to edit the package base to match yours.

        reobf {
            jar {
                extraLines "PK: tehnut/lib your/package/base/shade/lib"
            }
        }
        
* In your `jar {}` block, add this to exclude the `META-INF` shipped in LendingLibrary:

        configurations.shade.each { dep ->
            from(project.zipTree(dep)) {
                exclude 'META-INF', 'META-INF/**'
            }
        }