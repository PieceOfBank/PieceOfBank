// 보호자 UI - Header
import React from "react";
import { View, Image, Text } from "react-native";
import smallLogo from "../../assets/smallLogo.png";

// Store -> 보호자 메인페이지인 경우, <Link>Message</Link> 띄울 수 있게..

const Header = () => {
  return (
    <View className="bg-blue-400 dark:bg-blue-100 flex justify-center items-center">
      <Image source={smallLogo} className="w-12 h-12" />
    </View>
  );
};

export default Header;
