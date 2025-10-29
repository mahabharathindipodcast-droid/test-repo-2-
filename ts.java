from pydantic import BaseModel, Field
from typing import List, Optional
from datetime import datetime
from models.pubsub.pubsub_commit_event import PubSubCommitEvent

class Commit(BaseModel):
    """Model for individual commit data."""
    
    id: str = Field(..., description="Commit SHA")
    message: str = Field(..., description="Commit message")
    author_name: str = Field(..., description="Author name")
    author_email: str = Field(..., description="Author email")
    author_username: str = Field(..., description="Author username")
    files_added: List[str] = Field(..., description="List of files added")
    files_removed: List[str] = Field(..., description="List of files removed")
    files_modified: List[str] = Field(..., description="List of files modified")
    timestamp: str = Field(..., description="Commit timestamp")
    url: str = Field(..., description="API URL for the commit")
    html_url: str = Field(..., description="Web URL for the commit")
    tree_sha: str = Field(..., description="Tree SHA")
    parent_shas: List[str] = Field(default_factory=list, description="Parent commit SHAs")
