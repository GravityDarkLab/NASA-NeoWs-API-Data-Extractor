package com.darklab.asteroids.service;

/**
 * Represents a generic pair of two elements.
 *
 * This immutable record provides a way to group two values together without
 * having to create a custom class.
 *
 * @param <T>
 *            the type of the first element.
 * @param <U>
 *            the type of the second element.
 */
public record Pair<T, U>(T first, U second) {
}
