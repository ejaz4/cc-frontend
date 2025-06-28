import { Image } from 'expo-image';
import { Platform, StyleSheet, View, Text } from 'react-native';

import { HelloWave } from '@/components/HelloWave';
import ParallaxScrollView from '@/components/ParallaxScrollView';
import { ThemedText } from '@/components/ThemedText';
import { ThemedView } from '@/components/ThemedView';
import { ActivityWithTitle, screenKit } from '@/components/Screen';

export default function HomeScreen() {
  return (
    <ActivityWithTitle title={"Conversations"}>
      <View style={screenKit.screenLayout}>
        <Text>Hi</Text>
      </View>
    </ActivityWithTitle>
  );
}
