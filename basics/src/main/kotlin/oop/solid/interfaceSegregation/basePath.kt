package oop.solid.interfaceSegregation

import java.io.File
import java.nio.file.Paths

// this is simply used to get the path of the files package from within the interfaceSegregation package
val filesPath = Paths.get("").toAbsolutePath().toString() +
        File.separator + "basics" +
        File.separator + "src" +
        File.separator + "oop.solid.interfaceSegregation.main" +
        File.separator + "kotlin" +
        File.separator + "oop" +
        File.separator + "solid" +
        File.separator + "interfaceSegregation" +
        File.separator + "files" +
        File.separator
