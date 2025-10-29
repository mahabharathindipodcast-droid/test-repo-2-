from pydantic import BaseModel, Field
from typing import List, Optional
from datetime import datetime
from models.pubsub.pubsub_commit_event import PubSubCommitEvent

class Commit(BaseModel):
    """Model for individual commit data."""
    timestamp: str = Field(..., description="Commit timestamp")
    url: str = Field(..., description="API URL for the commit")
    html_url: str = Field(..., description="Web URL for the commit")
    tree_sha: str = Field(..., description="Tree SHA")
    parent_shas: List[str] = Field(default_factory=list, description="Parent commit SHAs")
