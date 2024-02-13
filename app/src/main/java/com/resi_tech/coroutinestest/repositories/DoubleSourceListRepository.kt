package com.resi_tech.coroutinestest.repositories

import com.resi_tech.coroutinestest.models.GroceryListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

class DoubleSourceListRepository: GroceryListRepository {
  override fun getNewlyAdded(): Flow<GroceryListItem> = merge(
    Source1GroceryListRepository().getNewlyAdded(),
    Source2GroceryListRepository().getNewlyAdded()
  )
}