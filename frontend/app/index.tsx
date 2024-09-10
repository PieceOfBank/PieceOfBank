import { Link } from "expo-router";
import { View, Text, ImageBackground } from 'react-native';

// MainPage

export default function HomeScreen() {
  return (
    <ImageBackground source={require('../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Welcome to the Home Page!</Text>
      <Text>여기가 메인 페이지..</Text>
      <Link href={'/profile'}>이동하기</Link>
      </View>
      </ImageBackground>
  );
}