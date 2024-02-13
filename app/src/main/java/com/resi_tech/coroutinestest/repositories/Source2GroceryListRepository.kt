package com.resi_tech.coroutinestest.repositories

import com.resi_tech.coroutinestest.models.GroceryListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

class Source2GroceryListRepository: GroceryListRepository {
  private val mockedGroceryList = arrayListOf<GroceryListItem>(
    GroceryListItem("6", "Pasta", 1.99, 1),
    GroceryListItem("7", "Rice", 2.99, 1),
    GroceryListItem("8", "Tomato Sauce", 3.99, 1),
    GroceryListItem("9", "Ground Beef", 4.99, 1),
    GroceryListItem("10", "Chicken", 5.99, 1)
  )

  override fun getNewlyAdded(): Flow<GroceryListItem> = flow {
    mockedGroceryList.forEach { item ->
      delay(12.seconds)
      emit(item)
    }
  }
}