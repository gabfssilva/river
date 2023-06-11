package com.river.core

import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineStart.LAZY

/**
 * Extension property on a suspend function that allows it to be called asynchronously.
 * The result of the suspend function is wrapped in a Deferred object.
 *
 * Example usage:
 * ```
 * suspend fun calculate(): Int = 2 + 2
 * val result = calculate().eagerAsync
 * println(result.await()) // prints 4
 * ```
 */
context(CoroutineScope)
val <T> (suspend () -> T).eagerAsync: Deferred<T>
    get() = async { invoke() }

/**
 * Extension property on a suspend function that allows it to be called asynchronously.
 * The result of the suspend function is wrapped in a Deferred object and is computed only upon the first invocation of Deferred.await or Deferred.join.
 *
 * Example usage:
 * ```
 * suspend fun calculate(): Int = 2 + 2
 * val result = calculate().lazyAsync
 * println(result.await()) // prints 4
 * ```
 */
context(CoroutineScope)
val <T> (suspend () -> T).lazyAsync: Deferred<T>
    get() = async(start = LAZY) { invoke() }

/**
 * Creates a lazily started coroutine which runs the provided suspend function.
 * The result of the suspend function is wrapped in a Deferred object and is computed only upon the first invocation of Deferred.await or Deferred.join.
 *
 * Example usage:
 * ```
 * suspend fun fetchUser(): User = userService.getUser(userId)
 *
 * coroutineScope {
 *     val userAsync by lazyAsync { fetchUser() }
 *     // The fetchUser() function will not be called until userDeferred.await() or userDeferred.join() is called.
 *     val user = userAsync.await() // fetches the user asynchronously when awaited
 * }
 * ```
 */
fun <T> CoroutineScope.lazyAsync(
    f: suspend () -> T
): Lazy<Deferred<T>> = lazy { f.lazyAsync }

/**
 * Function that takes two suspend functions and a zip function as parameters.
 * It executes the suspend functions asynchronously and then applies the zip function to their results.
 *
 * Example usage:
 * ```
 * suspend fun fetchUser(): User = userService.getUser(userId)
 * suspend fun fetchUserAddresses(): List<Address> = addressService.getUserAddresses(userId)
 *
 * val userData = suspendZip(
 *     ::fetchUser,
 *     ::fetchUserAddresses
 * ) { user, addresses -> DetailedUser(user, addresses) }
 *
 * // prints user data along with their addresses
 * println(userData)
 * ```
 */
suspend fun <T1, T2, R> suspendZip(
    first: suspend () -> T1,
    second: suspend () -> T2,
    zip: suspend (T1, T2) -> R
) = coroutineScope {
    val v1 = first.eagerAsync
    val v2 = second.eagerAsync

    zip(v1.await(), v2.await())
}

/**
 * Function that takes three suspend functions and a zip function as parameters.
 * It executes the suspend functions asynchronously and then applies the zip function to their results.
 *
 * Example usage:
 * ```
 * suspend fun fetchUser(): User =
 *     userService.getUser(userId)
 *
 * suspend fun fetchUserAddresses(): List<Address> =
 *     addressService.getUserAddresses(userId)
 *
 * suspend fun fetchUserOrders(): List<Order> =
 *     orderService.getUserOrders(userId)
 *
 * val userData = suspendZip(
 *     ::fetchUser,
 *     ::fetchUserAddresses,
 *     ::fetchUserOrders
 * ) { user, addresses, orders ->
 *     DetailedUser(user, addresses, orders)
 * }
 *
 * // prints user data along with their addresses and orders
 * println(userData)
 * ```
 */
suspend fun <T1, T2, T3, R> suspendZip(
    first: suspend () -> T1,
    second: suspend () -> T2,
    third: suspend () -> T3,
    zip: suspend (T1, T2, T3) -> R
) = coroutineScope {
    val v1 = first.eagerAsync
    val v2 = second.eagerAsync
    val v3 = third.eagerAsync

    zip(v1.await(), v2.await(), v3.await())
}

/**
 * Function that takes four suspend functions and a zip function as parameters.
 * It executes the suspend functions asynchronously and then applies the zip function to their results.
 *
 * Example usage:
 * ```
 * suspend fun fetchUser(): User =
 *     userService.getUser(userId)
 *
 * suspend fun fetchUserAddresses(): List<Address> =
 *     addressService.getUserAddresses(userId)
 *
 * suspend fun fetchUserOrders(): List<Order> =
 *     orderService.getUserOrders(userId)
 *
 * suspend fun fetchUserPayments(): List<Payment> =
 *     paymentService.getUserPayments(userId)
 *
 * val userData = suspendZip(
 *     ::fetchUser,
 *     ::fetchUserAddresses,
 *     ::fetchUserOrders,
 *     ::fetchUserPayments
 * ) { user, addresses, orders, payments ->
 *     DetailedUser(user, addresses, orders, payments)
 * }
 *
 * // prints user data along with their addresses, orders and payments
 * println(userData)
 * ```
 */
suspend fun <T1, T2, T3, T4, R> suspendZip(
    first: suspend () -> T1,
    second: suspend () -> T2,
    third: suspend () -> T3,
    fourth: suspend () -> T4,
    zip: suspend (T1, T2, T3, T4) -> R
) = coroutineScope {
    val v1 = first.eagerAsync
    val v2 = second.eagerAsync
    val v3 = third.eagerAsync
    val v4 = fourth.eagerAsync

    zip(v1.await(), v2.await(), v3.await(), v4.await())
}

/**
 * Function that takes five suspend functions and a zip function as parameters.
 * It executes the suspend functions asynchronously and then applies the zip function to their results.
 *
 * Example usage:
 * ```
 * suspend fun fetchUser(): User =
 *     userService.getUser(userId)
 *
 * suspend fun fetchUserAddresses(): List<Address> =
 *     addressService.getUserAddresses(userId)
 *
 * suspend fun fetchUserOrders(): List<Order> =
 *     orderService.getUserOrders(userId)
 *
 * suspend fun fetchUserPayments(): List<Payment> =
 *     paymentService.getUserPayments(userId)
 *
 * suspend fun fetchUserNotifications(): List<Notification> =
 *     notificationService.getUserNotifications(userId)
 *
 * val userData = suspendZip(
 *     ::fetchUser,
 *     ::fetchUserAddresses,
 *     ::fetchUserOrders,
 *     ::fetchUserPayments,
 *     ::fetchUserNotifications
 * ) { user, addresses, orders, payments, notifications ->
 *     DetailedUser(user, addresses, orders, payments, notifications)
 * }
 *
 * // prints user data along with their addresses, orders, payments and notifications
 * println(userData)
 * ```
 */
suspend fun <T1, T2, T3, T4, T5, R> suspendZip(
    first: suspend () -> T1,
    second: suspend () -> T2,
    third: suspend () -> T3,
    fourth: suspend () -> T4,
    fifth: suspend () -> T5,
    zip: suspend (T1, T2, T3, T4, T5) -> R
) = coroutineScope {
    val v1 = first.eagerAsync
    val v2 = second.eagerAsync
    val v3 = third.eagerAsync
    val v4 = fourth.eagerAsync
    val v5 = fifth.eagerAsync

    zip(v1.await(), v2.await(), v3.await(), v4.await(), v5.await())
}

/**
 * Function that takes six suspend functions and a zip function as parameters.
 * It executes the suspend functions asynchronously and then applies the zip function to their results.
 *
 * Example usage:
 * ```
 * suspend fun fetchUser(): User =
 *     userService.getUser(userId)
 *
 * suspend fun fetchUserAddresses(): List<Address> =
 *     addressService.getUserAddresses(userId)
 *
 * suspend fun fetchUserOrders(): List<Order> =
 *     orderService.getUserOrders(userId)
 *
 * suspend fun fetchUserPayments(): List<Payment> =
 *     paymentService.getUserPayments(userId)
 *
 * suspend fun fetchUserNotifications(): List<Notification> =
 *     notificationService.getUserNotifications(userId)
 *
 * suspend fun fetchUserPreferences(): Preferences =
 *     preferenceService.getUserPreferences(userId)
 *
 * val userData = suspendZip(
 *     ::fetchUser,
 *     ::fetchUserAddresses,
 *     ::fetchUserOrders,
 *     ::fetchUserPayments,
 *     ::fetchUserNotifications,
 *     ::fetchUserPreferences
 * ) { user, addresses, orders, payments, notifications, preferences ->
 *     DetailedUser(user, addresses, orders, payments, notifications, preferences)
 * }
 *
 * // prints user data along with their addresses, orders, payments, notifications and preferences
 * println(userData)
 * ```
 */
suspend fun <T1, T2, T3, T4, T5, T6, R> suspendZip(
    first: suspend () -> T1,
    second: suspend () -> T2,
    third: suspend () -> T3,
    fourth: suspend () -> T4,
    fifth: suspend () -> T5,
    sixth: suspend () -> T6,
    zip: suspend (T1, T2, T3, T4, T5, T6) -> R
) = coroutineScope {
    val v1 = first.eagerAsync
    val v2 = second.eagerAsync
    val v3 = third.eagerAsync
    val v4 = fourth.eagerAsync
    val v5 = fifth.eagerAsync
    val v6 = sixth.eagerAsync

    zip(v1.await(), v2.await(), v3.await(), v4.await(), v5.await(), v6.await())
}

/**
 * Function that takes seven suspend functions and a zip function as parameters.
 * It executes the suspend functions asynchronously and then applies the zip function to their results.
 *
 * Example usage:
 * ```
 * suspend fun fetchUser(): User =
 *     userService.getUser(userId)
 *
 * suspend fun fetchUserAddresses(): List<Address> =
 *     addressService.getUserAddresses(userId)
 *
 * suspend fun fetchUserOrders(): List<Order> =
 *     orderService.getUserOrders(userId)
 *
 * suspend fun fetchUserPayments(): List<Payment> =
 *     paymentService.getUserPayments(userId)
 *
 * suspend fun fetchUserNotifications(): List<Notification> =
 *     notificationService.getUserNotifications(userId)
 *
 * suspend fun fetchUserPreferences(): Preferences =
 *     preferenceService.getUserPreferences(userId)
 *
 * suspend fun fetchUserContacts(): List<Contact> =
 *     contactService.getUserContacts(userId)
 *
 * val userData = suspendZip(
 *     ::fetchUser,
 *     ::fetchUserAddresses,
 *     ::fetchUserOrders,
 *     ::fetchUserPayments,
 *     ::fetchUserNotifications,
 *     ::fetchUserPreferences,
 *     ::fetchUserContacts
 * ) { user, addresses, orders, payments, notifications, preferences, contacts ->
 *     DetailedUser(user, addresses, orders, payments, notifications, preferences, contacts)
 * }
 *
 * // prints user data along with their addresses, orders, payments, notifications, preferences and contacts
 * println(userData)
 * ```
 */
suspend fun <T1, T2, T3, T4, T5, T6, T7, R> suspendZip(
    first: suspend () -> T1,
    second: suspend () -> T2,
    third: suspend () -> T3,
    fourth: suspend () -> T4,
    fifth: suspend () -> T5,
    sixth: suspend () -> T6,
    seventh: suspend () -> T7,
    zip: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
) = coroutineScope {
    val v1 = first.eagerAsync
    val v2 = second.eagerAsync
    val v3 = third.eagerAsync
    val v4 = fourth.eagerAsync
    val v5 = fifth.eagerAsync
    val v6 = sixth.eagerAsync
    val v7 = seventh.eagerAsync

    zip(v1.await(), v2.await(), v3.await(), v4.await(), v5.await(), v6.await(), v7.await())
}
