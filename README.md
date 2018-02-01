### Description
Collection of build strategy for the jenkins pipeline.

#### The BuildOnDiscoverStrategyImpl:
This strategy will build your branches and PRs only if it has just been discovered. It's useful if you use a stash webhook to trigger your multipiline PRs. Not using this strategy leads to building all your PRs each time a change has been made between two scans.

