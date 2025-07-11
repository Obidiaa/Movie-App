package com.obidia.movieapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import com.obidia.movieapp.presentation.util.robotoFamily

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Composable
fun MovieAppTheme(
    content: @Composable() () -> Unit
) {
    val colorScheme = darkScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(
            displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = robotoFamily),
            displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = robotoFamily),
            displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = robotoFamily),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = robotoFamily),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = robotoFamily),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = robotoFamily),
            titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = robotoFamily),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = robotoFamily),
            titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = robotoFamily),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = robotoFamily),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = robotoFamily),
            bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = robotoFamily),
            labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = robotoFamily),
            labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = robotoFamily),
            labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = robotoFamily)
        ),
        content = content
    )
}

