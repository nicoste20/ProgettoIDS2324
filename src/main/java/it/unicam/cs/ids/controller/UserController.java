package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.List;

public class UserController {
    List<IUserPlatform> usersList;

    public void addCurator(IUserPlatform user, IUserPlatform platformManager) {
        if (platformManager.getUserType().equals(UserRole.PlatformManager)) {
            int index = usersList.indexOf(user);
            if (index != -1) {
                this.usersList.get(index).setRole(UserRole.Curator);
            }
        }
    }

    public void addAnimator(IUserPlatform user, IUserPlatform platformManager) {
        if (platformManager.getUserType().equals(UserRole.PlatformManager)) {
            int index = usersList.indexOf(user);
            if (index != -1) {
                this.usersList.get(index).setRole(UserRole.Animator);
            }
        }
    }

    public void changeRole() {
        for (IUserPlatform user : usersList) {
            if (user.getUserType().equals(UserRole.Contributor) && user.getPostCount() >= 10) {
                user.setRole(UserRole.ContributorAuthorized);
            }
        }
    }

}
