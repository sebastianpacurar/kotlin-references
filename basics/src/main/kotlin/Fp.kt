import java.io.File
import java.nio.file.Paths

object Fp {
    private fun getBasePath(specificPath: List<String>): String {
        val builder = StringBuilder()
        builder.append(Paths.get("").toAbsolutePath().toString())
        builder.append(File.separator).append("basics")
        builder.append(File.separator).append("src")
        builder.append(File.separator).append("main")
        builder.append(File.separator).append("kotlin")
        specificPath.forEach { path ->
            builder.append(File.separator).append(path)
        }
        builder.append(File.separator)
        return builder.toString()
    }

    val interfaceSegregationFp: String by lazy {
        getBasePath(listOf("oop", "solid", "interfaceSegregation", "files"))
    }

    val yieldFp: String by lazy {
        getBasePath(listOf("coroutines", "cooperation", "files"))
    }
}
