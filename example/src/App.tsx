import { View, StyleSheet } from 'react-native';
import SampleSvgXml from './SampleSvgXml';
import Section from './Section';

export default function App() {
  return (
    <View style={styles.container}>
      <Section title="Load svg with xml">
        <SampleSvgXml />
      </Section>
      <Section title="Load svg with url"></Section>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal: 24,
    paddingVertical: 24,
  },
});
