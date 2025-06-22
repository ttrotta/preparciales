package com.example.parcialvuelosayd.model

data class Country (val name: String, val boundingBox: BoundingBox) {
    override fun toString(): String {
        return name
    }
}