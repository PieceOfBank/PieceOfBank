import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';

const TransactionList = () => {
    interface CareItem {
        id: string;
        name: string;
    }
     // 임시 리스트 (거래)
     const careList: CareItem [] = [{id:'1', name:'9월 1일'}, {id:'2', name:'9월 2일'}, {id:'3', name:'9월 3일'}, {id:'4', name:'9월 4일'}, {id:'5', name:'9월 5일'}]
    return(
        <View className='bg-green-300'>
            <ScrollView>
                <Text>거래내역~~</Text>
                {careList.map((list, index) => (
                    <View key={index}>
                        <Text>{list.name}</Text>
                    </View>
                    ))}
            </ScrollView>
        </View>
    )
}

export default TransactionList
