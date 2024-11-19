package whatisup.kotlin.app.domain.mappers

import whatisup.kotlin.app.domain.persistence.models.Repo as PersistenceRepo
import whatisup.kotlin.app.domain.api.models.Repo as ApiRepo

fun ApiRepo.toPersistenceRepo() = PersistenceRepo(
    id = id,
    name = name,
    fullName = fullName,
    isPrivate = isPrivate,
    owner = owner.toPersistenceRepoUser(),
    htmlUrl = htmlUrl,
    description = description,
    isFork = isFork,
    url = url,
    homepage = homepage,
    size = size,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    hasIssues = hasIssues,
    hasProjects = hasProjects,
    hasDownloads = hasDownloads,
    hasWiki = hasWiki,
    hasPages = hasPages,
    hasDiscussions = hasDiscussions,
    forksCount = forksCount,
    mirrorUrl = mirrorUrl,
    isArchived = isArchived,
    isDisabled = isDisabled,
    openIssuesCount = openIssuesCount,
    topics = topics,
    visibility = visibility,
    forks = forks,
    openIssues = openIssues,
    watchers = watchers,
    defaultBranch = defaultBranch,
    score = score
)