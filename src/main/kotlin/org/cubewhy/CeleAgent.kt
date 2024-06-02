package org.cubewhy

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation
import java.security.ProtectionDomain


@Suppress("unused")
fun premain(arg: String?, inst: Instrumentation) {
    // patch anti anti agent check
    println("[CeleWrap] patching LC Anti AntiAgent check")
    println("Thanks https://github.com/koxx12-dev/LunarAntiAntiAgent")
    inst.addTransformer(object : ClassFileTransformer {
        override fun transform(
            loader: ClassLoader,
            targetClassName: String,
            targetClass: Class<*>?,
            protectionDomain: ProtectionDomain,
            buffer: ByteArray
        ): ByteArray {
            if (!targetClassName.startsWith("com/moonsworth/lunar/")) return buffer

            val reader: ClassReader = ClassReader(buffer)

            if (reader.interfaces.size !== 0) return buffer

            val node = ClassNode()
            reader.accept(node, 0)

            for (method in node.methods) {
                //extremely specific check, so it doesn't break anything else
                if ((method.name == "check") && method.desc == "()V" && method.access == (Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC)) {
                    println("Found Lunar Client Agent check method, patching... ($targetClassName)")
                    val inject = InsnList()
                    inject.add(InsnNode(Opcodes.RETURN))
                    method.instructions = inject

                    val writer: ClassWriter = ClassWriter(reader, 0)
                    node.accept(writer)
                    println("Successfully patched Lunar Client AntiAgent!")
                    return writer.toByteArray()
                }
            }

            return buffer
        }
    })
}