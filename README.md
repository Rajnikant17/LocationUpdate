# LocationUpdate

Library used :

1 Workmanager.
2 Room Database
3 Hilt Dependency injection
4 Fused Location API .
5 LiveData
6 ViewBinding

I have used Fused Location API for fetching the current location and then storing the
data into the Room database locally and then updating the UI using the Livedata and Viewbinding i.e whenever the Data changes in the
Room database Livedata will observe the changes and update the UI.

Implemented the Hilt Dependency inject for injecting the Objects at various places wherever it is required.

Note.

Google has updated its privacy policy for fetching the Location in background as it is  mention below :

"The Google Play store has updated its policy concerning device location, restricting background location
access to apps that need it for their core functionality and meet related policy requirements.
Adopting these best practices doesn't guarantee Google Play approves your app's usage of location in the background."

So i have assumed that if the app is killed , i'm not updating the location in the Database , although if the app is in Task Manager
i.e not in Foreground or even if all UI's are destroyed then also location will be updated in the Database.

I might have used Alarmanager and brodcastreceiver with Services , but some times in some devices it doesn't function properly but
workmanager gurantees the execution so i have used workmanager.

