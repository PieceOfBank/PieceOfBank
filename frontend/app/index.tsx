import { Link } from "expo-router";
import { View, Text } from 'react-native';

// MainPage

export default function HomeScreen() {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Welcome to the Home Page!</Text>
      <Text>dsagsdi</Text>
      <Link href={'/profile'}>이동하기</Link>
    </View>
  );
}