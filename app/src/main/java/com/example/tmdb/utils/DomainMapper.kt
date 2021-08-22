package com.example.tmdb.utils

interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

    fun toDomainList(initial: List<T>): List<DomainModel> = initial.map { mapToDomainModel(it) }

    fun fromDomainList(initial: List<DomainModel>): List<T> = initial.map { mapFromDomainModel(it) }
}