package com.resi_tech.coroutinestest.repositories

import com.resi_tech.coroutinestest.models.GroceryListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GroceryListRepository {
  fun getNewlyAdded(): Flow<GroceryListItem>
}