d_repos,  # currently active repositories
        'timestamp_webhook_received': datetime.now(UTC)
      })
    else:
      # Create typed installation event for repositories removed
      installation_event = InstallationEvent(
        installation_id=str(installation_id),
        action=data['action'],
        account=InstallationAccount(
          id=data['installation']['account']['id'],
          login=data['installation']['account']['login'],
          type=data['installation']['account']['type'],
        ),
        repositories=[
          Repository(
            id=repo['id'],
            name=repo['name'],
            full_name=repo['full_name'],
            private=repo['private'],
          ) for repo in repositories_removed
        ],
        permissions=data['installation']['permissions'],
        timestamp_webhook_received=datetime.now(UTC)
      )
      if db:
        installation_ref = db.collection(INSTALLATION_COLLECTION).document(str(installation_id))
        installation_ref.set(installation_event.model_dump())
    queue_service.publish_repository_modification_message(RepositoryModification(
      platform='github',
      installation_id=str(installation_id),
      project_id=None,  # GitHub doesn't have project_id concept like GitLab
      project_namespace_id=None,  # GitHub doesn't have project_namespace_id concept like GitLab
      owners=None,  # GitHub doesn't have owners concept like GitLab
      repositories_deleted=[{'full_name': repo['full_name']} for repo in repositories_removed],
      repositories_added=[]
    ))
