import SvgNative from '../specs/SvgNativeComponent';
import type { SvgViewNativeProps } from '../specs/SvgNativeComponent';
import type { ResizeMode } from '../types';

type SvgViewProps = Omit<SvgViewNativeProps, 'resizeMode'> & {
  resizeMode?: ResizeMode;
};

const SvgView = ({ resizeMode = 'contain', ...restProps }: SvgViewProps) => {
  return <SvgNative {...restProps} resizeMode={resizeMode} />;
};

export { SvgView, type SvgViewProps };
