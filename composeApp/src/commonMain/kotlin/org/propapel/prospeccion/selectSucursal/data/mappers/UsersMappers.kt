package org.propapel.prospeccion.selectSucursal.data.mappers

import org.propapel.prospeccion.selectSucursal.data.model.CustomerUserDto
import org.propapel.prospeccion.selectSucursal.data.model.InteractionUserDto
import org.propapel.prospeccion.selectSucursal.data.model.ProjectUserDto
import org.propapel.prospeccion.selectSucursal.data.model.PurchaseUserDto
import org.propapel.prospeccion.selectSucursal.data.model.ReminderUserDto
import org.propapel.prospeccion.selectSucursal.data.model.RoleDto
import org.propapel.prospeccion.selectSucursal.data.model.SucursaleDto
import org.propapel.prospeccion.selectSucursal.data.model.UserDto
import org.propapel.prospeccion.selectSucursal.data.model.UserResponseItem
import org.propapel.prospeccion.selectSucursal.domain.model.CustomerUser
import org.propapel.prospeccion.selectSucursal.domain.model.CustomerUser.Companion.stringToLocalDateTime
import org.propapel.prospeccion.selectSucursal.domain.model.InteractionUser
import org.propapel.prospeccion.selectSucursal.domain.model.ProjectUser
import org.propapel.prospeccion.selectSucursal.domain.model.PurchaseUser
import org.propapel.prospeccion.selectSucursal.domain.model.ReminderUser
import org.propapel.prospeccion.selectSucursal.domain.model.Role
import org.propapel.prospeccion.selectSucursal.domain.model.Sucursale
import org.propapel.prospeccion.selectSucursal.domain.model.User
import org.propapel.prospeccion.selectSucursal.domain.model.UserItem

fun UserResponseItem.mapToDomain(): UserItem{
    return UserItem(
        createdAt = createdAt,
        customers = customers?.map { it.mapToDomain() }?: emptyList(),
        email = email,
        id = id,
        image = image?:"",
        lastname = lastname,
        name = name,
        phone = phone?:"",
        puesto = puesto,
        refreshToken = refreshToken,
        roles = roleDtos?.map {
            it.mapToDomain()
        }?: emptyList(),
        sucursales = sucursaleDtos?.map {
            it.mapToDomain()
        }?: emptyList(),
        updatedAt = updatedAt
    )
}

fun SucursaleDto.mapToDomain(): Sucursale{
    return Sucursale(
        createdAt = createdAt,
        direccion = direccion,
        id = id,
        image = image?:"",
        nombre = nombre,
        updatedAt = updatedAt
    )
}

fun RoleDto.mapToDomain(): Role{
    return Role(
        createdAt = createdAt,
        id = id,
        image = image,
        name = name,
        route = route,
        updatedAt = updatedAt
    )
}

fun CustomerUserDto.mapToDomain(): CustomerUser{
    return CustomerUser(
        address = address?:"",
        companyName = companyName,
        contactName = contactName?:"",
        createdAt = stringToLocalDateTime(createdAt),
        customerId = customerId,
        email = email?:"",
        interactions = interactions.map {
            it.mapToDomain()
        },
        phoneNumber = phoneNumber?:"",
        progressLead =  progressLead,
        projects = projects?.map {
            it.mapToDomain()
        }?: emptyList(),
        purchaseUsers = purchases?.map {
            it.mapToDomain()
        }?: emptyList(),
        reminderUsers = reminders?.map {
            it.mapToDomain()
        }?: emptyList(),
        typeOfClient = typeOfClient,
        updatedAt = updatedAt,
        user = user.mapToDomain()
    )
}

fun ReminderUserDto.mapToDomain(): ReminderUser{
    return ReminderUser(
        createdAt = createdAt,
        updatedAt = updatedAt,
        reminderId = reminderId,
        isCompleted = isCompleted,
        typeAppointment = typeAppointment,
        reminderDate = reminderDate,
        description = description
    )
}
fun PurchaseUserDto.mapToDomain(): PurchaseUser{
    return PurchaseUser(
        amount = amount,
        productServiceName = productServiceName,
        purchaseId = purchaseId,
        isIntoProduct = isIntoProduct,
        purchaseDate = purchaseDate
    )
}

fun ProjectUserDto.mapToDomain(): ProjectUser{
    return ProjectUser(
        createdAt = stringToLocalDateTime(createdAt),
        id = id,
        status = status,
        progress = progress,
        valorProject = valorProject,
        prioridad = prioridad,
        nameProject = nameProject,
        isCancel = isCancel,
        updatedAt = updatedAt
    )
}
fun InteractionUserDto.mapToDomain(): InteractionUser{
    return InteractionUser(
        interactionDate = interactionDate,
        interactionId = interactionId,
        interactionType = interactionType,
        notes = notes
    )
}

fun UserDto.mapToDomain(): User {
    return User(
        createdAt = createdAt,
        updatedAt = updatedAt,
        id = id,
        name = name,
        image = image?:"",
        refreshToken = refreshToken,
        phone = phone,
        email = email,
        lastname = lastname,
        password = password,
        puesto = puesto
    )
}