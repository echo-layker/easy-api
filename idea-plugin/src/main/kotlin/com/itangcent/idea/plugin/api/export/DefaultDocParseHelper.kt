package com.itangcent.idea.plugin.api.export

import com.google.inject.Inject
import com.google.inject.Singleton
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMember
import com.intellij.psi.PsiMethod
import com.itangcent.intellij.psi.PsiClassHelper
import java.util.regex.Pattern

@Singleton
open class DefaultDocParseHelper : DocParseHelper {

    @Inject
    private val psiClassHelper: PsiClassHelper? = null

    @Inject
    private val linkResolver: LinkResolver? = null

    override fun resolveLinkInAttr(attr: String?, psiMember: PsiMember): String? {
        if (attr.isNullOrBlank()) return attr

        if (attr.contains("@link")) {
            val pattern = Pattern.compile("\\{@link (.*?)\\}")
            val matcher = pattern.matcher(attr)

            val sb = StringBuffer()
            while (matcher.find()) {
                matcher.appendReplacement(sb, "")
                val linkClassAndMethod = matcher.group(1)
                val linkClassName = linkClassAndMethod.substringBefore("#")
                val linkMethodOrProperty = linkClassAndMethod.substringAfter("#", "").trim()
                val linkClass = psiClassHelper!!.resolveClass(linkClassName, psiMember) ?: continue
                if (linkMethodOrProperty.isBlank()) {
                    sb.append(linkResolver!!.linkToClass(linkClass))
                } else {
                    val methodOrProperty = psiClassHelper.resolvePropertyOrMethodOfClass(linkClass, linkMethodOrProperty)
                            ?: continue
                    when (methodOrProperty) {
                        is PsiMethod -> sb.append(linkResolver!!.linkToMethod(methodOrProperty))
                        is PsiField -> sb.append(linkResolver!!.linkToProperty(methodOrProperty))
                        else -> sb.append("[$linkClassAndMethod]")
                    }
                }
            }
            matcher.appendTail(sb)
            return sb.toString()
        }

        return attr
    }
}