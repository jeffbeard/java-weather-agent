# Global Git/GH conventions
- Always branch off `main` (never work directly on `main`).
- Branch names should be `feature/<short-name>` or `bugfix/<short-name>`.
- To open PRs use:
    gh pr create --base main \
                 --head <branch> \
                 --title "TYPE: brief summary" \
                 --body "Link to issue…"
- Squash when merging, use our standard PR template.
