package com.resi_tech.coroutinestest

import com.resi_tech.coroutinestest.models.GroceryListItem
import com.resi_tech.coroutinestest.repositories.DoubleSourceListRepository
import com.resi_tech.coroutinestest.repositories.Source1GroceryListRepository
import com.resi_tech.coroutinestest.repositories.Source2GroceryListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class GroceryListRepositoryTest {
  private val mockedGroceryList = arrayListOf<GroceryListItem>(
    GroceryListItem("1", "Milk", 2.99, 1),
    GroceryListItem("2", "Bread", 1.99, 1),
    GroceryListItem("3", "Eggs", 3.99, 1),
    GroceryListItem("4", "Butter", 2.99, 1),
    GroceryListItem("5", "Cheese", 4.99, 1)
  )

  private val mockedGroceryList2 = arrayListOf<GroceryListItem>(
    GroceryListItem("6", "Pasta", 1.99, 1),
    GroceryListItem("7", "Rice", 2.99, 1),
    GroceryListItem("8", "Tomato Sauce", 3.99, 1),
    GroceryListItem("9", "Ground Beef", 4.99, 1),
    GroceryListItem("10", "Chicken", 5.99, 1)
  )

  private val mergedGroceryList = arrayListOf<GroceryListItem>(
    GroceryListItem("1", "Milk", 2.99, 1),//500 from start
    GroceryListItem("2", "Bread", 1.99, 1),//1000
    GroceryListItem("6", "Pasta", 1.99, 1),//1200
    GroceryListItem("3", "Eggs", 3.99, 1),//1500
    GroceryListItem("4", "Butter", 2.99, 1),//2000
    GroceryListItem("7", "Rice", 2.99, 1), //2400
    GroceryListItem("5", "Cheese", 4.99, 1),//2500
    GroceryListItem("8", "Tomato Sauce", 3.99, 1), //3600
    GroceryListItem("9", "Ground Beef", 4.99, 1), //4800
    GroceryListItem("10", "Chicken", 5.99, 1) //6000
  )

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `test Source1GroceryListRepository`() = runTest {
    val repository = Source1GroceryListRepository()

    repository.getNewlyAdded()
      .collectIndexed() { index, item ->
        println("Item: $item, Timestamp: ${System.currentTimeMillis()}")
        assertEquals(item, mockedGroceryList[index])
      }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `test Source2GroceryListRepository`() = runTest {
    val repository = Source2GroceryListRepository()

    repository.getNewlyAdded()
      .collectIndexed() { index, item ->
        println("Item: $item, Timestamp: ${System.currentTimeMillis()}")
        assertEquals(item, mockedGroceryList2[index])
      }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `test DoubleSourceGroceryListRepository`() = runTest {
    val repository = DoubleSourceListRepository()
    val result = arrayListOf<GroceryListItem>()
    repository.getNewlyAdded()
      .collectIndexed() { index, item ->
        println("Item: $item, Timestamp: ${System.currentTimeMillis()}")
        result.add(item)
      }
    assertEquals(result, mergedGroceryList)
  }

  @Test
  fun `test getNewlyAdded with error`() = runTest {
    val repository = Mockito.spy(Source1GroceryListRepository())
    Mockito.`when`(repository.getNewlyAdded()).thenReturn(flow { throw Exception("Error fetching grocery list") })

    repository
      .getNewlyAdded()
      .catch { e -> assertEquals(e.message, "Error fetching grocery list") }
      .collect()
  }
}