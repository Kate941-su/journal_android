package com.kaitokitaya.jounal.data.model

import java.time.LocalDate
import java.util.UUID

data class Journal(val id: UUID, val date: LocalDate? = null, val title: String, val content: String)