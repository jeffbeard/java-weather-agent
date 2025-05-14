# Global Git/GH conventions
- Always branch off `main` (never work directly on `main`).
- Follow [Conventional Commits] (https://www.conventionalcommits.org/en/v1.0.0/) specification for commits and branches
- Branch names should be `feature/<short-name>` or `bugfix/<short-name>`. Committ prefix options are limited to:
    * feature
    * bugfix
    * chore
    * docs
    * refactor 
    * test
    * perf
    * ci
- To open PRs use:
    gh pr create --base main \
                 --head <branch> \
                 --title "TYPE: brief summary" \
                 --body "Link to issue…"
- Squash when merging, use our standard PR template.
