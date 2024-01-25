package it.unicam.cs.ids.model.user;
/**
 * Enumeration representing different user roles in the system.
 * Each role defines a certain level of access or permissions.
 */
public enum UserRole {
    /**
     * Represents a contributor user who need the curator authorization to publish in the platform.
     */
    Contributor,

    /**
     * Represents an authorized contributor.
     */
    ContributorAuthorized,
    /**
     * Represents a vistor for the platform.
     */
    Tourist,
    /**
     * Represents a visitor user who need curator authorization to publish in the platform.
     */
    TouristAuthorized,
    /**
     * Represents a curator user with specialized access and permissions.
     */
    Curator,
    /**
     * Represents an animator user with can create contest in the platform.
     */
    Animator
}
