package com.itangcent.idea.plugin.api.export

import com.google.inject.ImplementedBy
import com.itangcent.common.model.MethodDoc
import com.itangcent.common.model.Param

@ImplementedBy(DefaultMethodDocHelper::class)
interface MethodDocHelper {

    fun setName(methodDoc: MethodDoc, name: String)

    fun appendDesc(methodDoc: MethodDoc, desc: String?)

    fun addParam(methodDoc: MethodDoc, param: Param)

    fun setRet(methodDoc: MethodDoc, ret: Any?, retAttr: String?)
}

//region utils------------------------------------------------------------------
fun MethodDocHelper.addParam(methodDoc: MethodDoc, paramName: String, value: Any?, desc: String?) {
    addParam(methodDoc, paramName, value, false, desc)
}

fun MethodDocHelper.addParam(methodDoc: MethodDoc, paramName: String, value: Any?, required: Boolean, desc: String?) {
    val param = Param()
    param.name = paramName
    param.value = value
    param.required = required
    param.desc = desc
    this.addParam(methodDoc, param)
}

fun MethodDocHelper.setRet(methodDoc: MethodDoc, ret: Any?) {
    this.setRet(methodDoc, ret, null)
}

//endregion utils------------------------------------------------------------------