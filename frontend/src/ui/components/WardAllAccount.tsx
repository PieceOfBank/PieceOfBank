import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity, Image, FlatList, StyleSheet } from 'react-native';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'expo-router';

const WardAllAccount = () => {

    const router = useRouter();

     return (
            <View className='w-80 h-12 bg-sky-300 flex-row items-center justify-between'>
                <View className='mx-2'>
                    <Text>이름</Text>
                </View>
                <View className='mx-2'>
                    <TouchableOpacity className='w-24 h-8 rounded-xl justify-center bg-sky-500'
                    // onPress={} disabled={disabled}
                    >
                        <View className='flex-row justify-center'>
                        <Text className='text-white text-center font-bold'>대표 계좌</Text>
                        <Text className='text-white text-center font-bold text-yellow-300'> ★ </Text>
                        </View>
                    </TouchableOpacity>
                </View>
            </View>
         
       );
}

export default WardAllAccount