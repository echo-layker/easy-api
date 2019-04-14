package com.itangcent.idea.plugin.utils

import com.itangcent.common.utils.GsonUtils

object GsonExUtils {

    fun toJson(bean: Any?): String {
        val beanWithClass = BeanWithClass()
        if (bean != null) {
            beanWithClass.c = bean.javaClass.name
            beanWithClass.j = GsonUtils.toJson(bean)
        }
        return GsonUtils.toJson(bean)
    }

    fun <T> fromJson(json: String): T? {
        val beanWithClass = GsonUtils.fromJson(json, BeanWithClass::class)
        if (beanWithClass.c == null) return null
        val cls: Class<T> = Class.forName(beanWithClass.c) as Class<T>
        return GsonUtils.fromJson(beanWithClass.j!!, cls)
    }

    class BeanWithClass {
        //class
        var c: String? = null
        //json
        var j: String? = null
    }
}