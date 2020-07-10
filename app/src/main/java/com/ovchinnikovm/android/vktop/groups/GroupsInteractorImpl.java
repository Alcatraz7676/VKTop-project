package com.ovchinnikovm.android.vktop.groups;

public class GroupsInteractorImpl implements GroupsInteractor {
    private GroupsRepository repository;

    public GroupsInteractorImpl(GroupsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getGroups();
    }
}
