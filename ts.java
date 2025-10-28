
  /**
   * Get commit with both stats and diff in a single API call.
   * API: GET /repos/{owner}/{repo}/commits/{sha}
   */
  public async getCommitWithDiff(
    owner: string,
    repo: string,
    sha: string,
  ): Promise<{
    stats: { additions: number; deletions: number };
    diff: string;
  }> {
    try {
      const commit = await this.request<RawCommit>(
        `/repos/${owner}/${repo}/commits/${sha}`,
      );

      const stats = {
        additions: commit.stats?.additions ?? 0,
        deletions: commit.stats?.deletions ?? 0,
      };

      // Combine all file patches into a single diff
      const patches =
        commit.files?.map((file) => file.patch).filter(Boolean) || [];
      const diff = patches.join("\n");

      return { stats, diff };
    } catch (error) {
      console.error(
        `Error fetching commit with diff for ${sha} in ${owner}/${repo}:`,
        error,
      );
      return {
        stats: { additions: 0, deletions: 0 },
        diff: "",
      };
    }
  }
