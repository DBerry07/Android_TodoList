package self.dwjonesberry.simpletodolist.data

interface NavigationViewModel {

    val navigateToMainScreen: (() -> Unit)?
    val navigateToAddScreenWithArguments: ((MyTask) -> Unit)?
    val popBackStack: (() -> Unit)?

}