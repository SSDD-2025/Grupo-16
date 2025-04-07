/**
 * This package contains custom exceptions for handling specific error scenarios, as well as a global exception handler.
 *
 * The custom exceptions in this package are designed to represent specific error conditions:
 * - ArtistAlreadyExistsException
 * - ArtistNotFoundException
 * - ConcertNotFoundException
 * - ImageException
 * - TicketNotFoundException
 * - UserAlreadyExistsException
 * - UserNotFoundException
 *
 * These exceptions are used to manage specific cases where an entity either already exists or cannot
 * be found, allowing for more granular error handling.
 *
 * The package also includes a GlobalExceptionHandler that is responsible for handling these custom
 * exceptions globally. This handler provides structured error responses in JSON format, ensuring
 * consistent and informative error messages are returned.
 */
package es.ticketmaster.entrega1.service.exceptions;