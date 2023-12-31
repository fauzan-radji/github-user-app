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

- [x] Favorite user
  - [x] Add to favorite
  - [x] Remove from favorite
  - [x] Show favorite user
- [x] Theme
  - [x] Switch theme
  - [x] Save theme preference
  - [x] Load theme preference
  - [x] Text and Icon color based on theme

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
  - [x] Create HomeFragmentTest
    - [x] Search & Click User Testing
  - [ ] Create DetailFragmentTest
    - [ ] Swipe Tab Testing
    - [ ] Click Tab Testing
    - [ ] Click Followers Testing
    - [ ] Click Following Testing
    - [ ] Click Share Testing
    - [ ] Click Favorite Testing
    - [ ] Click Unfavorite Testing
    - [ ] Click Back Testing
  - [ ] Create FavoriteFragmentTest
    - [ ] Click FavoriteUser Testing
    - [ ] Click Unfavorite Testing
  - [ ] Create ThemeFragmentTest
- [x] Database
  - [x] Create UserRepository
  - [x] Create UserDao
  - [x] Create GithubUserDatabase
  - [x] Create UserEntity
- [x] Detail Page
  - [x] FAB for Favorite in DetailFragment
  - [x] Create/Insert favorite user
  - [x] Delete favorite user
- [x] Favorite Page
  - [x] Create FavoriteFragment
  - [x] Layout for FavoriteFragment
  - [x] Create FavoriteViewModel
  - [x] Read favorite user
- [ ] Theme
  - [ ] Create ThemePreference
  - [ ] Create ThemeViewModel
  - [ ] Create ThemeFragment