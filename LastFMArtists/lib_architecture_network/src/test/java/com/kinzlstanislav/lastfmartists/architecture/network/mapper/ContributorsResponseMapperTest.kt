package com.kinzlstanislav.lastfmartists.architecture.network.mapper

import com.kinzlstanislav.lastfmartists.architecture.core.model.Contributor
import com.kinzlstanislav.lastfmartists.architecture.network.response.GithubRepositoryContributionAuthorResponse
import com.kinzlstanislav.lastfmartists.architecture.network.response.GithubRepositoryContributorResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ContributorsResponseMapperTest {

    private companion object {
        val INPUT = listOf(
            GithubRepositoryContributorResponse(
                contributor = GithubRepositoryContributionAuthorResponse(
                    name = "stanislav",
                    avatarUrl = "url"
                ),
                numberOfCommits = 20
            ),
            GithubRepositoryContributorResponse(
                contributor = null,
                numberOfCommits = 10
            ),
            GithubRepositoryContributorResponse(
                contributor = GithubRepositoryContributionAuthorResponse(
                    name = null,
                    avatarUrl = null
                ),
                numberOfCommits = null
            )
        )

        val EXPECTED_RESULT = listOf(
            Contributor(
                loginName = "stanislav",
                avatarUrl = "url",
                numberOfCommits = 20
            ),
            Contributor(
                loginName = "",
                avatarUrl = "",
                numberOfCommits = 10
            ),
            Contributor(
                loginName = "",
                avatarUrl = "",
                numberOfCommits = 0
            )
        )
    }

    private val subject = ContributorsResponseMapper()

    @Test
    fun `mapFromContributorsResponse()`() {
        assertThat(subject.mapFromContributorsResponse(INPUT)).isEqualTo(EXPECTED_RESULT)
    }
}