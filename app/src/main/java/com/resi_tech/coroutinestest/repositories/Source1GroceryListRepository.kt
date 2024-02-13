package com.resi_tech.coroutinestest.repositories

import com.resi_tech.coroutinestest.models.GroceryListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

class Source1GroceryListRepository: GroceryListRepository {
  private val mockedGroceryList = arrayListOf<GroceryListItem>(
    GroceryListItem("1", "Milk", 2.99, 1),
    GroceryListItem("2", "Bread", 1.99, 1),
    GroceryListItem("3", "Eggs", 3.99, 1),
    GroceryListItem("4", "Butter", 2.99, 1),
    GroceryListItem("5", "Cheese", 4.99, 1)
  )

  override fun getNewlyAdded(): Flow<GroceryListItem> = flow {
    mockedGroceryList.forEach { item ->
      delay(5.seconds)
      emit(item)
    }
  }
}