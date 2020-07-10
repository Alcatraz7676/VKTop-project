package com.ovchinnikovm.android.vktop.group;

public class GroupInteractorImpl implements GroupInteractor{
    private GroupRepository repository;

    public GroupInteractorImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Integer groupId) {
        repository.getPostsCount(groupId);
    }
}
