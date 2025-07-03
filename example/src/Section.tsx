import type { PropsWithChildren } from 'react';
import { StyleSheet, Text, View } from 'react-native';

type SectionProps = PropsWithChildren<{
  title: string;
}>;

const Section = ({ title, children }: SectionProps) => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>{title}</Text>
      {children}
    </View>
  );
};

export default Section;

const styles = StyleSheet.create({
  container: {
    alignItems: 'flex-start',
    marginBottom: 20,
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 12,
  },
});
