# Github User

This is a simple project to show user information from Github API. This application was created as a submission to the [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14/) course in Dicoding.

## Submission 1 Checklist

### Required

- [x] Search user
  - [x] Loading state
- [x] User details
  - [x] Loading state
  - [x] Tab Layout and ViewPager
    - [x] Followers
      - [x] Loading state
    - [x] Following
      - [x] Loading state

### Optional

- [ ] Give `retry` action when API request is failed
- [ ] On typing search
- [x] Custom splash screen
- [x] Custom icon
- [x] Custom color
- [ ] Custom font
- [x] Image for Glide placeholder on loading state
- [ ] Skeleton screen for loading state
- [x] Use Github PAT (expire in September 25th, 2021)

## Submission 2 Checklist

### Required

- [ ] Favorite user
  - [ ] Add to favorite
  - [ ] Remove from favorite
  - [ ] Show favorite user
- [ ] Theme
  - [ ] Switch theme
  - [ ] Save theme preference
  - [ ] Load theme preference
  - [ ] Text and Icon color based on theme

### Optional

- [ ] Share user
- [ ] Testing
  - [ ] Unit testing
  - [ ] Instrumentation/UI testing

### Step by step

- [ ] Unit Testing
  - [ ] Create HomeViewModelTest
  - [ ] Create DetailViewModelTest
  - [ ] Create FavoriteViewModelTest
  - [ ] Create ThemeViewModelTest
- [ ] UI Testing
  - [ ] Create HomeFragmentTest
  - [ ] Create DetailFragmentTest
  - [ ] Create FavoriteFragmentTest
  - [ ] Create ThemeFragmentTest
- [ ] Database
  - [ ] Create Repository for Favorite
  - [ ] Create FavoriteDao
  - [ ] Create FavoriteDatabase
  - [ ] Create FavoriteEntity
  - [ ] Create FavoriteViewModel
- [ ] Detail Page
  - [ ] FAB for Favorite in DetailFragment
  - [ ] Create/Insert favorite user
  - [ ] Delete favorite user
- [ ] Favorite Page
  - [ ] Create FavoriteFragment
  - [ ] Layout for FavoriteFragment
  - [ ] Read favorite user
- [ ] Theme
  - [ ] Create ThemePreference
  - [ ] Create ThemeViewModel
  - [ ] Create ThemeFragment