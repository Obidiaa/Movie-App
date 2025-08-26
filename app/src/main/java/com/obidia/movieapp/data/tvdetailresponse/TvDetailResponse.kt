package com.obidia.movieapp.data.tvdetailresponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvDetailResponse(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("created_by")
    val createdBy: List<CreatedBy?>? = null,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int?>? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("genres")
    val genres: List<Genre?>? = null,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("in_production")
    val inProduction: Boolean? = null,
    @SerialName("languages")
    val languages: List<String?>? = null,
    @SerialName("last_air_date")
    val lastAirDate: String? = null,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: String? = null,
    @SerialName("networks")
    val networks: List<Network?>? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int? = null,
    @SerialName("origin_country")
    val originCountry: List<String?>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<String>? = null,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry?>? = null,
    @SerialName("seasons")
    val seasons: List<Season?>? = null,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
) {

}

@Serializable
data class CreatedBy(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("credit_id")
    val creditId: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("gender")
    val gender: Int? = null,
    @SerialName("profile_path")
    val profilePath: String? = null
)

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)

@Serializable
data class LastEpisodeToAir(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("vote_average")
    val voteAverage: Int? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("air_date")
    val airDate: String? = null,
    @SerialName("episode_number")
    val episodeNumber: Int? = null,
    @SerialName("episode_type")
    val episodeType: String? = null,
    @SerialName("production_code")
    val productionCode: String? = null,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("season_number")
    val seasonNumber: Int? = null,
    @SerialName("show_id")
    val showId: Int? = null,
    @SerialName("still_path")
    val stillPath: String? = null
)

@Serializable
data class Network(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("logo_path")
    val logoPath: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("origin_country")
    val originCountry: String? = null
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String? = null,
    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Season(
    @SerialName("air_date")
    val airDate: String? = null,
    @SerialName("episode_count")
    val episodeCount: Int? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("season_number")
    val seasonNumber: Int? = null,
    @SerialName("vote_average")
    val voteAverage: Int? = null
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String? = null,
    @SerialName("iso_639_1")
    val iso6391: String? = null,
    @SerialName("name")
    val name: String? = null
)