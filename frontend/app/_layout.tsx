import { Stack } from "expo-router";
import { useEffect } from "react";

import Toast from "react-native-toast-message";
import { registerForPushNotificationsAsync } from "../src/utils/NotificationToken";

export default function Layout() {

  useEffect(() => {
    console.log("Layout initialized with Firebase");

    registerForPushNotificationsAsync().then(token => {
      console.log('Expo Push Token:', token);
    });
    
  }, []);

  return (
    <>
      <Stack screenOptions={{headerShown:false}}/>
      <Toast />
    </>
  );
}
