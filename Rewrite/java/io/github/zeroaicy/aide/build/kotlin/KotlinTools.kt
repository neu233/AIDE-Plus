package io.github.zeroaicy.aide.build.kotlin

import com.aide.codemodel.DefaultTools
import com.aide.codemodel.api.FileEntry
import com.aide.codemodel.api.Model
import com.aide.codemodel.api.SyntaxTree
import com.aide.codemodel.api.abstraction.Language


/**
 * @author: 罪慾
 * @github: https://github.com/neu233/
 * @mail: 3115093767@qq.com
 * @createTime: 2025/2/6
 */


class KotlinTools(model: Model) : DefaultTools(model) {

    override fun er(
        syntaxTree: SyntaxTree?,
        fileEntry: FileEntry,
        language: Language?,
        line: Int,
        column: Int
    ) {
        val pathString = fileEntry.pathString
        val codeCompleterCallback = model.codeCompleterCallback
        if (pathString.endsWith(".kt")
            || pathString.endsWith(".kts")
        ) {
            codeCompleterCallback.listStarted()
            codeCompleterCallback.listElementKeywordFound("abstract")
            codeCompleterCallback.listElementKeywordFound("actual")
            codeCompleterCallback.listElementKeywordFound("annotation")
            codeCompleterCallback.listElementKeywordFound("as")
            codeCompleterCallback.listElementKeywordFound("as?")
            codeCompleterCallback.listElementKeywordFound("assert")
            codeCompleterCallback.listElementKeywordFound("break")
            codeCompleterCallback.listElementKeywordFound("by")
            codeCompleterCallback.listElementKeywordFound("catch")
            codeCompleterCallback.listElementKeywordFound("class")
            codeCompleterCallback.listElementKeywordFound("companion")
            codeCompleterCallback.listElementKeywordFound("const")
            codeCompleterCallback.listElementKeywordFound("constuctor")
            codeCompleterCallback.listElementKeywordFound("continue")
            codeCompleterCallback.listElementKeywordFound("data")
            codeCompleterCallback.listElementKeywordFound("do")
            codeCompleterCallback.listElementKeywordFound("else")
            codeCompleterCallback.listElementKeywordFound("enum")
            codeCompleterCallback.listElementKeywordFound("expect")
            codeCompleterCallback.listElementKeywordFound("finally")
            codeCompleterCallback.listElementKeywordFound("for")
            codeCompleterCallback.listElementKeywordFound("fun")
            codeCompleterCallback.listElementKeywordFound("get")
            codeCompleterCallback.listElementKeywordFound("if")
            codeCompleterCallback.listElementKeywordFound("implements")
            codeCompleterCallback.listElementKeywordFound("import")
            codeCompleterCallback.listElementKeywordFound("interface")
            codeCompleterCallback.listElementKeywordFound("in")
            codeCompleterCallback.listElementKeywordFound("infix")
            codeCompleterCallback.listElementKeywordFound("init")
            codeCompleterCallback.listElementKeywordFound("internal")
            codeCompleterCallback.listElementKeywordFound("inline")
            codeCompleterCallback.listElementKeywordFound("is")
            codeCompleterCallback.listElementKeywordFound("lateinit")
            codeCompleterCallback.listElementKeywordFound("native")
            codeCompleterCallback.listElementKeywordFound("object")
            codeCompleterCallback.listElementKeywordFound("open")
            codeCompleterCallback.listElementKeywordFound("operator")
            codeCompleterCallback.listElementKeywordFound("or")
            codeCompleterCallback.listElementKeywordFound("out")
            codeCompleterCallback.listElementKeywordFound("override")
            codeCompleterCallback.listElementKeywordFound("package")
            codeCompleterCallback.listElementKeywordFound("private")
            codeCompleterCallback.listElementKeywordFound("protected")
            codeCompleterCallback.listElementKeywordFound("public")
            codeCompleterCallback.listElementKeywordFound("reified")
            codeCompleterCallback.listElementKeywordFound("return")
            codeCompleterCallback.listElementKeywordFound("sealed")
            codeCompleterCallback.listElementKeywordFound("set")
            codeCompleterCallback.listElementKeywordFound("super")
            codeCompleterCallback.listElementKeywordFound("this")
            codeCompleterCallback.listElementKeywordFound("throw")
            codeCompleterCallback.listElementKeywordFound("try")
            codeCompleterCallback.listElementKeywordFound("typealias")
            codeCompleterCallback.listElementKeywordFound("val")
            codeCompleterCallback.listElementKeywordFound("var")
            codeCompleterCallback.listElementKeywordFound("vararg")
            codeCompleterCallback.listElementKeywordFound("when")
            codeCompleterCallback.listElementKeywordFound("where")
            codeCompleterCallback.listElementKeywordFound("while")
            codeCompleterCallback.listElementKeywordFound("true")
            codeCompleterCallback.listElementKeywordFound("false")
            codeCompleterCallback.listElementKeywordFound("null")
            // j6 -> J8
            codeCompleterCallback.J8(
                fileEntry,
                language,
                line,
                column,
                tp(fileEntry, line, column),
                false,
                false
            )
            return
        }
        codeCompleterCallback.a8(fileEntry, line, column)
    }

    override fun tp(fileEntry: FileEntry, line: Int, column: Int): Int {
        val s = fileEntry.Mr(line, column)
        var length = s.length - 1
        while (length >= 0) {
            if (!Character.isLetter(s[length]) && s[length] != '.') {
                break
            }
            length--
        }
        return length + 2
    }
}