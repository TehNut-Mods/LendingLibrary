# LendingLibrary

A shadeable lib used in many of my projects.

Currently available for:

* Minecraft 1.9

## How to add to your project

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
                          
### Working Examples

* [Soul Shards - The Old Ways](https://github.com/TehNut/Soul-Shards-The-Old-Ways/blob/1.9/build.gradle)
* [ResourcefulCrops](https://github.com/TehNut/ResourcefulCrops/blob/1.9/build.gradle)

## Basic Usage

This is a fairly simple library to use. Here is a basic skeleton:

```java
@Mod(modid = MyMod.MODID, name = MyMod.MODID, version = "1.0.0")
public class MyMod {
    public static final String MODID = "mymod";
    
    public LendingLibrary library;
    
    public MyMod() {
        library = new LendingLibrary(MODID);
    }
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Registers all Items/Blocks/EventHandlers with the appropriate annotation
        library.registerObjects(event);
    }
}
```

## Example Usage

A few of my mods use this lib. I'm not aware of any others.

* [ResourcefulCrops](https://github.com/TehNut/ResourcefulCrops/tree/1.9)
* [Soul Shards - The Old Ways](https://github.com/TehNut/Soul-Shards-The-Old-Ways/tree/1.9)

LendingLibrary also includes [a test mod](https://github.com/TehNut/LendingLibrary/tree/1.9/src/main/java/tehnut/lib/test) in it's repository that covers the most basic uses. This is stripped from the `jar` when built.
